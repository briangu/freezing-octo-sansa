
def is_palindrome(n):
    if n < 10:
        return False
    q = n
    k = 0
    while n:
        k = k*10 + n % 10
        n = n//10
    return k == q


if __name__ == "__main__":
    assert not is_palindrome(1)
    assert not is_palindrome(10)
    assert not is_palindrome(201)
    assert is_palindrome(101)
    assert is_palindrome(14041)
