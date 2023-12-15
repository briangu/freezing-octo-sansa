

def max_subsequence(arr):
    start = 0
    stop = 0
    best = float("-inf")
    t = 0
    j = 0

    for i,x in enumerate(arr):
        t += x
        if t > best:
            best = t
            start = j
            stop = i
        elif t < 0:
            t = 0
            j = i+1

    return start, stop, best


if __name__ == "__main__":
    sample = [1, 2, -5, 4, 7, -2]
    start, stop, best = max_subsequence(sample)
    print(start, stop, best, sample[start:stop+1])

