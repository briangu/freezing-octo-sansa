class Node:
    def __init__(self, v, left=None, right=None):
        self.v  = v
        self.left = left
        self.right = right

    def predecessor(self):
        n = self.left
        while n.right:
            n = n.right
        return n

    def successor(self):
        n = self.right
        while n.left:
            n = n.left
        return n

    def print(self, prefix=""):
        if self.left:
            self.left.print(prefix="<")
        print(prefix + str(self.v))
        if self.right:
            self.right.print(prefix=">")

class BinaryTree:

    def __init__(self):
        self.root = None

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

    def print(self):
        if self.root is None:
            return
        self.root.print()


import unittest

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

if __name__ == '__main__':
    unittest.main()
