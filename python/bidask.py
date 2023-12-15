
class Node:
    def __init__(self, k, parent=None, keys=None, children=None):
        self.parent = parent
        self.k = k
        self.keys = keys or []
        self.children = children or []

    def insert(self, k, v):
        node = None
        # find the node where k belongs
        if self.keys:
            if self.keys[0][0] <= k and k <= self.keys[-1][0]:
            # key range
                pass
        else:
            # empty root
            assert self.parent is None
            node = self
        if node:
            node._insert(k, v)

    def _insert(self, k, v):
        if len(self.keys) < self.k:
            pos = None
            i = 0
            while i < len(self.keys) and pos is None:
                if self.keys[i][0] == k:
                    # duplicate value
                    pos = i
                elif self.keys[i][0] > k:
                    pos = i - 1
                i += 1


    def delete(self, k, v):
        pass

    # def _rotate_left(self):
    #     pass

    # def _rotate_right(self):
    #     pass

