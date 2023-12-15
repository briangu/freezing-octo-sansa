
class Node:
    def __init__(self, k=2, parent=None, keys=None, children=None):
        self.parent = parent
        self.k = k
        self.keys = keys or []
        self.children = children or []

    def is_leaf(self):
        return len(self.children) == 0

    def is_full(self):
        return len(self.keys) == self.k

    def insert_at_leaf(self, k, v):
        assert self.is_leaf()
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        if i < len(self.keys) and self.keys[i][0] == k:
            self.keys[i][1].append(v)
        else:
            self.keys.insert(i, (k,[v]))
        assert self.is_leaf() or (len(self.keys) + 1 == len(self.children))

    def push_up(self, k, v, node, left, right):
        assert not self.is_leaf()
        i = 0
        while i < len(self.keys) and self.keys[i][0] < k:
            i += 1
        assert (i < len(self.keys) and self.keys[i][0] != k) or (i >= len(self.keys))
        self.keys.insert(i, (k,[v]))
        child_idx = self.children.index(node)
        self.children[child_idx] = left
        self.children.insert(i+1, right)
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
            if node.parent.is_full():
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
        if len(current.keys) > self.k:
            current = self.split_node(current)

    def delete(self, k, v):
        if not self.root:
            return
        self.root.delete(k, v)


import unittest

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
        btree.insert(10, 'value10')
        btree.insert(20, 'value20')
        btree.insert(15, 'value15')  # This should cause a split

        self.assertEqual(len(btree.root.keys), 1)
        self.assertEqual(btree.root.keys[0][0], 15)
        self.assertEqual(len(btree.root.children), 2)
        # Check left child
        self.assertEqual(btree.root.children[0].keys, [(10, ['value10'])])
        # Check right child
        self.assertEqual(btree.root.children[1].keys, [(20, ['value20'])])

    def test_insert_with_root_split(self):
        btree = BTree(k=2)
        # Insert enough keys to cause a root split
        btree.insert(10, 'value10')
        btree.insert(20, 'value20')
        btree.insert(30, 'value30')

        self.assertIsNotNone(btree.root)
        self.assertEqual(len(btree.root.keys), 1)
        self.assertEqual(len(btree.root.children), 2)
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

if __name__ == "__main__":
    unittest.main()
