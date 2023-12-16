
class Cell:

  def __init__(self, fn, deps=None):
    self.deps = deps
    self.fn = fn

  def __call__(self):
    return self.fn(*[x() for x in self.deps]) if self.deps else self.fn()


if __name__ == "__main__":
  a = Cell(lambda: 1)
  b = Cell(lambda: 2)
  c = Cell(lambda x,y: x+y, deps=[a,b])
  assert c() == 3

