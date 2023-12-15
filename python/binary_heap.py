import math

class BinaryHeap:

    def __init__(self, h):
        self.h = h
        self.arr = [0]*int(math.pow(2,h) - 1)
        self.size = 0

    def insert(self, x):
        if self.size == len(self.arr):
            raise RuntimeError("heap is full")

        self.arr[self.size] = x
        self.size += 1
        self.sift_up(self.size - 1)

    @staticmethod
    def get_left(i):
        return 2*i + 1

    @staticmethod
    def get_right(i):
        return 2*i + 2

    @staticmethod
    def get_parent(i):
        return (i-1)//2

    def swap(self, i, j):
        self.arr[i], self.arr[j] = self.arr[j], self.arr[i]

    def sift_up(self, i):
        if i == 0:
            return

        parent_idx = self.get_parent(i)
        if self.arr[parent_idx] > self.arr[i]:
            self.swap(i, parent_idx)
            self.sift_up(parent_idx)

    def delete_min(self):
        if self.size == 0:
            raise RuntimeError("heap is empty")

        m = self.arr[0]
        self.arr[0] = self.arr[self.size - 1]
        self.size -= 1
        self.sift_down(0)
        return m

    def sift_down(self, i):
        left_idx = self.get_left(i)
        right_idx = self.get_right(i)

        min_idx = i
        if left_idx < self.size and self.arr[left_idx] < self.arr[i]:
            min_idx = left_idx
        if right_idx < self.size and self.arr[right_idx] < self.arr[i]:
            min_idx = right_idx

        if min_idx != i:
            self.swap(i, min_idx)
            self.sift_down(min_idx)

    def heapify(self):
        for i in range(self.size//2,-1,-1):
            self.sift_down(i)

    def merge(self, heap):
        self.h += heap.h
        new_arr = [0]*int(math.pow(2,self.h) - 1)
        for i in range(self.size):
            new_arr[i] = self.arr[i]
        for i in range(heap.size):
            new_arr[i+self.size] = heap.arr[i]
        self.arr = new_arr
        self.size += heap.size
        self.heapify()

    def print(self, i=0, level=0, prefix="*"):
        if i >= self.size:
            return
        print(" "*level, end="")
        print(prefix + str(self.arr[i]))
        self.print(self.get_left(i), level + 1, prefix="<")
        self.print(self.get_right(i), level + 1, prefix=">")

if __name__ == "__main__":
    import random
    random.seed(0)
    bh = BinaryHeap(10)
    bh2 = BinaryHeap(10)
    bh.print()
    for i in range(5):
      bh.insert(random.randint(0,100))
      bh2.insert(random.randint(0,100))
      bh.print()
      print("__________")
    bh.print()
    print("done")

    print("__________")
    print("delete min")
    bh.delete_min()
    bh.print()

    print("__________")
    print("merge")
    print("bh:")
    bh.print()
    print("bh2:")
    bh2.print()
    bh.merge(bh2)
    print("final:")
    bh.print()

