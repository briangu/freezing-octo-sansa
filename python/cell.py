
class Cell:

  def __init__(self, fn, deps=None, name=None):
    self.deps = deps or []
    self.fn = fn
    self.cache = None
    self.name = name

  def __call__(self):
    if self.cache is None:
      self.cache = self.fn(*[x() for x in self.deps])
      if self.name:
        print(f"node: {self.name} filling cache {self.cache} from {[x.name for x in self.deps]}")
    elif self.name:
      print(f"node: {self.name} using cache {self.cache} from {[x.name for x in self.deps]}")
    return self.cache


if __name__ == "__main__":
  a = Cell(lambda: 1, name="a")
  b = Cell(lambda: 2, name="b")
  c = Cell(lambda x,y: x+y, deps=[a,b], name="c")
  assert c() == 3
  d = Cell(lambda x: x*2, deps=[c], name="d")
  e = Cell(lambda x: x*4, deps=[c], name="e")
  f = Cell(lambda x,y: x+y, deps=[d,e], name="f")
  assert f() == 2*3+4*3
