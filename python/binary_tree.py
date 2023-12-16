class Node:
    def __init__(self, v, left=None, right=None):
        self.v  = v
        self.left = left
        self.right = right

    def predecessor(self):
        n = self.left
        while n and n.right:
            n = n.right
        return n

    def successor(self):
        n = self.right
        while n and n.left:
            n = n.left
        return n

    def print(self, prefix=""):
        if self.left:
            self.left.print(prefix="<")
        print(prefix + str(self.v))
        if self.right:
            self.right.print(prefix=">")

    def traverse(self):
        yield self.v
        if self.left:
            for x in self.left.traverse():
                yield x
        if self.right:
            for x in self.right.traverse():
                yield x


class BinaryTree:

    def __init__(self, root=None):
        self.root = root

    def insert(self, v):
        if self.root is None:
            self.root = Node(v)
            return

        n = self.root
        while n:
            if v < n.v:
                if n.left is None:
                    n.left = Node(v)
                    return
                else:
                    n = n.left
            elif v > n.v:
                if n.right is None:
                    n.right = Node(v)
                    return
                else:
                    n = n.right
            else:
                break

    def delete(self, v):
        self.root = self._delete_recursive(self.root, v)

    @staticmethod
    def _delete_recursive(n, v):
        if n is None:
            return None

        if v < n.v:
            n.left = BinaryTree._delete_recursive(n.left, v)
        elif v > n.v:
            n.right = BinaryTree._delete_recursive(n.right, v)
        else:
            if n.left is None and n.right is None:
                return None
            if n.left is None or n.right:
                n.v = n.successor().v
                n.right = BinaryTree._delete_recursive(n.right, n.v)
            else:
                n.v = n.predecessor().v
                n.left = BinaryTree._delete_recursive(n.left, n.v)
        return n

    def print(self, root=None, label=""):
        if label:
            print()
            print(label)
        root = root or self.root
        if self.root is None:
            return
        self.root.print()
        if label:
            print()


    @staticmethod
    def sorted_array_to_bst(arr):
        if not arr:
            return None
        mid = len(arr) // 2
        n = Node(arr[mid])
        n.left = BinaryTree.sorted_array_to_bst(arr[:mid])
        n.right = BinaryTree.sorted_array_to_bst(arr[mid+1:])
        return n

    def rebalance(self):
        node = self.root
        if node is None:
            return None
        arr = [x for x in node.traverse()]
        arr.sort()
        return BinaryTree(BinaryTree.sorted_array_to_bst(arr))

    @staticmethod
    def tree_height(root):
        if root is None:
            return 0
        if root.left is None and root.right is None:
            return 1
        return 1 + BinaryTree.tree_height(root.left) + BinaryTree.tree_height(root.right)

    def traverse(self):
        node = self.root
        if node is None:
            return []
        return list(node.traverse())


import unittest
import random

# Your Node and BinaryTree classes go here

class TestBinaryTree(unittest.TestCase):

    def test_insertion(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        self.assertEqual(bt.root.v, 10)
        self.assertEqual(bt.root.left.v, 5)
        self.assertEqual(bt.root.right.v, 15)

    def test_deletion_leaf_node(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        bt.delete(15)
        bt.print()
        self.assertEqual(bt.root.right, None)

    def test_deletion_node_with_one_child(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        bt.insert(12)
        bt.delete(15)
        self.assertEqual(bt.root.right.v, 12)

    def test_deletion_node_with_two_children(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        bt.insert(12)
        bt.insert(18)
        bt.delete(15)
        self.assertIn(bt.root.right.v, [12, 18])

    def test_tree_structure(self):
        bt = BinaryTree()
        values = [10, 5, 15, 2, 7, 12, 18]
        for v in values:
            bt.insert(v)
        bt.delete(10)
        print(bt.root.v)
        self.assertEqual(bt.root.v, 12)
        self.assertEqual(bt.root.left.v, 5)
        self.assertEqual(bt.root.right.v, 15)

    def test_deletion_node_with_two_children_and_successor_has_right_child(self):
        bt = BinaryTree()
        bt.insert(20)
        bt.insert(10)
        bt.insert(30)
        bt.insert(25)
        bt.insert(40)
        bt.delete(20)
        self.assertEqual(bt.root.v, 25)
        self.assertEqual(bt.root.right.left, None)

    def test_predecessor_successor_on_leaf_nodes(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        print()
        bt.print()
        print()
        self.assertIsNone(bt.root.left.predecessor())
        self.assertIsNone(bt.root.right.successor())

    def test_deletion_node_with_one_child_correctly(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.insert(5)
        bt.insert(15)
        bt.insert(20)
        bt.delete(15)
        self.assertEqual(bt.root.right.v, 20)

    def test_deletion_of_root_node_when_single_node(self):
        bt = BinaryTree()
        bt.insert(10)
        bt.delete(10)
        self.assertIsNone(bt.root)

    def test_deletion_of_nonexistent_node(self):
        bt = BinaryTree()
        values = [10, 5, 15]
        for v in values:
            bt.insert(v)
        bt.delete(20)
        self.assertEqual(bt.root.v, 10)
        self.assertEqual(bt.root.left.v, 5)
        self.assertEqual(bt.root.right.v, 15)

    def test_rebalance_empty_tree(self):
        bt = BinaryTree()
        rebalanced_root = bt.rebalance()
        self.assertIsNone(rebalanced_root)

    def test_rebalance_single_node_tree(self):
        bt = BinaryTree()
        bt.insert(10)
        rebalanced_bt = bt.rebalance()
        self.assertEqual(rebalanced_bt.root.v, 10)
        self.assertIsNone(rebalanced_bt.root.left)
        self.assertIsNone(rebalanced_bt.root.right)

    def test_rebalance_linear_tree(self):
        bt = BinaryTree()
        for i in range(1, 6):  # Creating a linearly skewed tree
            bt.insert(i)
        print("test_rebalance_linear_tree:bt")
        bt.print(label="random tree")
        rebalanced_bt = bt.rebalance()
        print(type(rebalanced_bt))
        print("test_rebalance_linear_tree:rebalanced_bt")
        rebalanced_bt.print(label="reblanaced_bt:")
        arr = rebalanced_bt.traverse()
        self.assertEqual(len(arr),len(list(range(1,6))))
        self.assertNotEqual(rebalanced_bt.root.v, 1)  # Root should be different now
        self.assertLessEqual(BinaryTree.tree_height(rebalanced_bt.root), BinaryTree.tree_height(bt.root))  # Check if height is reduced
        self.assertEqual(arr, list(range(1,6)))

    def test_rebalance_random_tree(self):
        bt = BinaryTree()
        random_values = [random.randint(1, 100) for _ in range(10)]
        for value in random_values:
            bt.insert(value)
        original_height = BinaryTree.tree_height(bt.root)
        rebalanced_bt = bt.rebalance()
        self.assertLessEqual(BinaryTree.tree_height(rebalanced_bt.root), original_height)


if __name__ == '__main__':
    unittest.main()
