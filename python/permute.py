import math
import unittest

def permute(arr, i=0, results=None):
    if arr is None:
        return None
    if len(arr) == 0:
        return [[]]
    if results is None:
        results = []
    if i == len(arr):
        tmp = arr[:]
        if tmp not in results:
            results.append(tmp)
        return results
    for j in range(i,len(arr)):
        arr[i],arr[j] = arr[j],arr[i]
        permute(arr, i+1, results)
        arr[i],arr[j] = arr[j],arr[i]
    return results

class TestPermuteFunction(unittest.TestCase):

    def test_empty_array(self):
        self.assertEqual(permute([]), [[]])

    def test_single_element(self):
        self.assertEqual(permute([1]), [[1]])

    def test_multiple_elements(self):
        result = permute([1, 2, 3])
        expected = [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
        self.assertEqual(sorted(result), sorted(expected))

    def test_repeating_elements(self):
        result = permute([1, 1, 2])
        expected = [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        self.assertEqual(sorted(result), sorted(expected))

    def test_large_array(self):
        arr = list(range(1, 6))  # Adjust size for testing
        result = permute(arr)
        self.assertEqual(len(result), math.factorial(len(arr)))

# Run the tests
if __name__ == '__main__':
    unittest.main()
