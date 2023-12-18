import unittest


class Node:
    def __init__(self, k=2, parent=None, keys=None, children=None):
        self.parent = parent
        self.k = k
        self.keys = keys or []
        self.children = children or []

    def is_leaf(self):
        return len(self.children) == 0

    def is_underflow(self):
        return False if self.parent is None else len(self.keys) < self.k//2

    def need_split(self):
        return len(self.keys) > 2*self.k

    def find(self, k, v=None):
        if not self.keys:
            return None
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        if i < len(self.keys) and self.keys[i][0] == k:
            return self if v is None or v in self.keys[i][1] else None
        return None if self.is_leaf() else self.children[i].find(k,v)

    def insert_at_leaf(self, k, v):
        assert self.is_leaf()
        assert not isinstance(v, list)
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        if i < len(self.keys) and self.keys[i][0] == k:
            self.keys[i][1].append(v)
        else:
            self.keys.insert(i, (k,[v]))
        assert self.is_leaf() or (len(self.keys) + 1 == len(self.children))

    def push_up(self, k, v, child, child_left, child_right):
        assert not self.is_leaf()
        assert isinstance(v, list)
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        assert (i < len(self.keys) and self.keys[i][0] != k) or (i >= len(self.keys))
        self.keys.insert(i, (k,v))
        child_idx = self.children.index(child)
        self.children[child_idx] = child_left
        self.children.insert(i+1, child_right)
        assert len(self.keys) + 1 == len(self.children)

    def traverse(self, results):
        if self.is_leaf():
            results.extend(self.keys)
        else:
            assert self.is_leaf() or (len(self.keys) + 1 == len(self.children))
            i = 0
            while i < len(self.keys):
                self.children[i].traverse(results)
                results.append(self.keys[i])
                i += 1
            if self.children:
                self.children[-1].traverse(results)

    def delete(self, k, v=None):
        if not self.keys:
            return None
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        found = False
        if i < len(self.keys) and self.keys[i][0] == k:
            if v is None:
                # delete the entire key
                del self.keys[i]
                found = True
            elif v in self.keys[i][1]:
                self.keys[i][1].remove(v)
                if len(self.keys[i][1]) == 0:
                    # values are empty, so del entire key
                    del self.keys[i]
                found = True
        return self if found else self.children[i].delete(k,v) if not self.is_leaf() else None

    def merge_with_sibling(self):
        if self.parent is None:
            return
        # get parent key to merge into sibling
        idx = self.parent.children.index(self)
        left_sibling = idx > 0
        sibling_idx = idx - 1 if left_sibling else idx + 1
        sibling = self.parent.children[sibling_idx]
        parent_key = self.parent.keys[sibling_idx]
        del self.parent.keys[sibling_idx]
        del self.parent.children[idx]
        # get sibling
        if left_sibling:
            # put keys after left sibling's
            sibling.keys.append(parent_key)
            sibling.keys.extend(self.keys)
            if self.children:
                sibling.children.extend(self.children)
        else:
            # put keys before right sibling's
            sibling.keys.insert(0,parent_key)
            sibling.keys = self.keys + sibling.keys
            if self.children:
                sibling.children = self.children + sibling.children


class BTree:
    def __init__(self, k):
        self.k = k
        self.root = Node(self.k)

    def traverse(self):
        if self.root:
            results = []
            self.root.traverse(results)
        return results

    def find(self, k, v=None):
        return self.root.find(k, v=v) if self.root else None

    def split_node(self, node):
        print("split: ", node.keys)
        m_idx = len(node.keys)//2
        m = node.keys[m_idx]
        left = Node(self.k, parent=node.parent, keys=node.keys[:m_idx], children=node.children[:m_idx])
        right = Node(self.k, parent=node.parent, keys=node.keys[m_idx+1:], children=node.children[m_idx+1:])

        for child in left.children:
            child.parent = left
        for child in right.children:
            child.parent = right

        if node.parent:
            node.parent.push_up(m[0], m[1], node, left, right)
            if node.parent.need_split():
                self.split_node(node.parent)
        else:
            self.root = Node(self.k, keys=[m], children=[left, right])
            left.parent = self.root
            right.parent = self.root

    def insert(self, k, v):
        current = self.root
        if not current:
            self.root = Node(k=self.k)
            self.root.keys.append((k,[v]))

        while not current.is_leaf():
            chosen = None

            assert len(current.keys) == len(current.children) - 1

            # Determine which child to follow
            i = 0
            while i < len(current.keys):
                if k < current.keys[i][0]:
                    chosen = current.children[i]
                    break
                i += 1

            if i >= len(current.keys):
                chosen = current.children[-1]

            assert chosen

            current = chosen

        # Insert the key in the leaf node
        current.insert_at_leaf(k, v)

        # Handle the case where the leaf node is full and needs splitting
        if current.need_split():
            current = self.split_node(current)

    def _merge(self, node):
        if not node.parent:
            # root, nothing to do
            return
        if node.parent:
            # inner or leaf node
            node.merge_with_sibling()
            if node.parent.is_underflow():
                self._merge(node.parent)

    def delete(self, k, v=None):
        if not self.root:
            return
        node = self.root.delete(k, v)
        if node and node.is_underflow():
            self._merge(node)


class TestBTree(unittest.TestCase):
    def test_insert_into_empty_tree(self):
        btree = BTree(k=2)
        btree.insert(10, 'value10')
        self.assertIsNotNone(btree.root)
        self.assertEqual(btree.root.keys, [(10, ['value10'])])

    def test_insert_into_leaf_without_split(self):
        btree = BTree(k=2)
        btree.insert(10, 'value10')
        btree.insert(5, 'value5')
        self.assertEqual(len(btree.root.keys), 2)
        self.assertEqual(btree.root.keys[0], (5, ['value5']))
        self.assertEqual(btree.root.keys[1], (10, ['value10']))

    def test_insert_with_leaf_split(self):
        btree = BTree(k=2)
        for i in range(4):
            btree.insert(i*10, f'value{i*10}')
        btree.insert(15, 'value15')  # This should cause a split

        self.assertEqual(len(btree.root.keys), 1)
        self.assertEqual(btree.root.keys[0][0], 15)
        self.assertEqual(len(btree.root.children), 2)
        # Check left child
        self.assertEqual(btree.root.children[0].keys, [(0, ['value0']), (10, ['value10'])])
        # Check right child
        self.assertEqual(btree.root.children[1].keys, [(20, ['value20']), (30, ['value30'])])

    def test_insert_with_root_split(self):
        btree = BTree(k=2)
        # Insert enough keys to cause a root split
        btree.insert(10, 'value10')
        btree.insert(20, 'value20')
        btree.insert(30, 'value30')

        self.assertIsNotNone(btree.root)
        self.assertEqual(len(btree.root.keys), 3)
        self.assertEqual(len(btree.root.children), 0)
        # More assertions to verify the structure after split

    def test_multiple_inserts(self):
        btree = BTree(k=3)
        keys = [10, 20, 5, 15, 25, 30, 25, 3, 8]
        for key in keys:
            btree.insert(key, f'value{key}')
        r = btree.traverse()
        keys = list(set(keys))
        keys.sort()
        print(keys)
        print(r)
        for k,v in zip(keys,r):
            self.assertEqual(k,v[0])


class TestNodeFindMethod(unittest.TestCase):
    def setUp(self):
        # Setup a BTree for testing
        self.btree = BTree(k=2)
        # Insert some keys and values
        self.btree.insert(10, 'value10')
        self.btree.insert(20, 'value20')
        self.btree.insert(5, 'value5')
        self.btree.insert(15, 'value15')
        self.btree.insert(25, 'value25')

    def test_find_key_in_leaf(self):
        node = self.btree.find(10)
        self.assertIsNotNone(node)
        self.assertIn((10, ['value10']), node.keys)

    def test_find_key_in_non_leaf(self):
        self.btree.insert(30, 'value30')  # This will cause a split and 20 will be in a non-leaf
        node = self.btree.find(20)
        self.assertIsNotNone(node)
        self.assertIn((20, ['value20']), node.keys)

    def test_find_non_existent_key(self):
        node = self.btree.find(100)
        self.assertIsNone(node)

    def test_find_key_with_specific_value_in_leaf(self):
        node = self.btree.find(10, 'value10')
        self.assertIsNotNone(node)
        self.assertIn((10, ['value10']), node.keys)

    def test_find_key_with_non_existent_value(self):
        node = self.btree.find(10, 'nonexistent')
        self.assertIsNone(node)

    def test_find_in_empty_tree(self):
        empty_tree = BTree(k=2)
        node = empty_tree.find(10)
        self.assertIsNone(node)


class TestBTreeDeletion(unittest.TestCase):

    def setUp(self):
        # Initialize a BTree for testing
        self.tree = BTree(k=2)
        # Populate the tree with known values
        for key, value in [(1, 'a'), (2, 'b'), (3, 'c')]:
            self.tree.insert(key, value)

    def test_delete_from_empty_tree(self):
        empty_tree = BTree(k=2)
        empty_tree.delete(1)
        self.assertEqual(empty_tree.traverse(), [])

    def test_delete_non_existent_key(self):
        original_structure = self.tree.traverse()
        self.tree.delete(99)  # Assume 99 is not in the tree
        self.assertEqual(self.tree.traverse(), original_structure)

    def test_delete_key_from_leaf(self):
        self.tree.delete(1)  # Assuming 1 is in a leaf
        self.assertNotIn((1, ['a']), self.tree.traverse())
        # Additional checks for tree structure can be added

    def test_delete_key_with_specific_value(self):
        self.tree.insert(4, 'd1')
        self.tree.insert(4, 'd2')
        self.tree.delete(4, 'd1')
        self.assertIn((4, ['d2']), self.tree.traverse())
        self.assertNotIn((4, ['d1']), self.tree.traverse())

    def test_delete_key_with_all_values(self):
        self.tree.delete(2)
        self.assertNotIn((2, ['b']), self.tree.traverse())

    def test_delete_key_from_non_leaf(self):
        # Assuming key 3 is in a non-leaf node
        self.tree.delete(3)
        self.assertNotIn((3, ['c']), self.tree.traverse())

    def test_underflow_and_merging(self):
        # Assuming deleting key 5 causes underflow
        self.tree.delete(5)
        # Add checks to verify the tree structure after merging

    def test_root_deletion_and_tree_height_decrease(self):
        # Assuming root has a single key
        self.tree.delete(self.tree.root.keys[0][0])
        # Check the new root and the height of the tree

    def test_complex_deletion_scenario(self):
        # Perform a series of deletions
        self.tree.delete(6)
        self.tree.delete(7)
        # ... more deletions
        # Add checks to verify the tree structure after multiple deletions


if __name__ == '__main__':
    unittest.main()

