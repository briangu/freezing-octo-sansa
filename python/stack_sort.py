class StackSort:

    @staticmethod
    def sort(a):
        b = []

        while a:
            x = a.pop()
            while b and b[-1] < x:
                a.append(b.pop())
            b.append(x)

        while b:
            a.append(b.pop())


if __name__ == "__main__":
    import random
    random.seed(0)

    stack_a = []
    for i in range(32):
      stack_a.append(random.randint(0,100))

    print(stack_a)
    StackSort.sort(stack_a)
    print(stack_a)

