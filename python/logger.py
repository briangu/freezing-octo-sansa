import random
import time

class Node:
    def __init__(self, start, end, partition, next):
        self.start = start
        self.end = end
        self.partition = partition
        self.next = next


def create_node(start, end, columns):
    k = end - start + 1
    partition = {c:[None]*k for c in columns}
    return Node(start, end, partition, None)



def create_partitions(n, k, start_ts, columns):
    """
    n: number of nodes
    k: partition size per node
    """
    # make some fake data that spans n nodes and k partitions
    # each node is non-overlapping range start -> end inclusive

    start = start_ts
    end = start + k - 1
    root = create_node(start, end, columns)
    current = root

    i = 1
    while i < n:
        start += k
        end += k
        current.next = create_node(start, end, columns)
        current = current.next
        i += 1

    return root


def enum_nodes(node):
    while node:
        yield node
        node = node.next


def in_range(node, start, end):
    """
                |node.start node.end|
    |start end|
                |start end|
                          |start end|
                                     |start end|
    |start         end|
                            |start         end|
    """
    if node.start <= start and start <= node.end:
        return True
    if node.start <= end and end <= node.end:
        return True
    return False


def slice_partition(node, start, end, columns):
    """
    extract the partition columsn and subset the rows according to start end
    """
    assert start <= node.end
    assert end >= node.start
    ps = max(node.start, start) - node.start
    pe = (min(node.end, end) - node.start) + 1
    return {c:node.partition[c][ps:pe] for c in columns}


def select_range(root, start, end, columns):
    return [slice_partition(x, start, end, columns) for x in enum_nodes(root) if in_range(x, start, end)]


def find_node(root, ts):
    node = root
    last = None
    while node and not (node.start <= ts and ts <= node.end):
        last = node
        node = node.next
    if node is None:
        k = last.end - last.start + 1
        start = last.start + k
        end = last.end + k
        columns = list(last.partition.keys())
        last.next = create_node(start, end, columns)
        node = last.next
        while node and not (node.start <= ts and ts <= node.end):
            start = node.start + k
            end = node.end + k
            node.next = create_node(start, end, columns)
            node = node.next
    return node


def store_random_data(root, start, end, points, columns):
    v = round(random.random() * 100,2)
    k = end - start + 1
    for i in range(points):
        # ts = random.randint(start, end)
        ts = start + (i%k)
        node = find_node(root, ts)
        if not node:
            continue
        offset = ts - node.start
        for c in columns:
            # v = round(random.random() * 100,2)
            node.partition[c][offset] = i * v


root = create_partitions(1, 100, 0, ['a','b','c'])
print("-------")
print("initial:")
print(list([(x.start,x.end) for x in enum_nodes(root)]))
print("-------")

ts_start = time.perf_counter()
store_random_data(root, 50, 500, 10000, ['b'])
ts_end = time.perf_counter()
print(1/((ts_end-ts_start)/10000))

ts_start = time.perf_counter()
r = select_range(root, 50, 100, ['b'])
ts_end = time.perf_counter()
print(1/(ts_end-ts_start))
for x in r:
    print(x)

print("-------")
print("final:")
print(list([(x.start,x.end) for x in enum_nodes(root)]))
print("-------")