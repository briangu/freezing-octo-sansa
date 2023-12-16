
class _Cell:

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


class Cell:

  def __init__(self, fn, name=None):
    self.fn = fn
    self.name = name

  def __call__(self, *args):
    if not callable(self.fn) and args:
      raise RuntimeError("constants are not callable")
    class _eval:
      def __init__(self, fn, name):
        self.fn = fn
        self.name = name
        self.cache = None
      def __call__(self):
        if not callable(self.fn):
          return self.fn
        if self.cache is None:
          self.cache = self.fn(*[x() for x in args])
          if self.name:
            print(f"node: {self.name} filling cache {self.cache} from {[x.name for x in args]}")
        elif self.name:
          print(f"node: {self.name} using cache {self.cache} from {[x.name for x in args]}")
        return self.cache
    return _eval(self.fn, self.name)


if __name__ == "__main__":
  a = Cell(1)()
  b = Cell(2)()
  c = Cell(lambda x,y: x+y)(a,b)
  assert c() == 3

  a = Cell(lambda: 1)()
  b = Cell(lambda: 2)()
  c = Cell(lambda x,y: x+y)(a,b)
  assert c() == 3

  a = Cell(lambda: 1, name="a")()
  b = Cell(lambda: 2, name="b")()
  c = Cell(lambda x,y: x+y, name="c")(a,b)
  assert c() == 3
  d = Cell(lambda x: x*2, name="d")(c)
  e = Cell(lambda x: x*4, name="e")(c)
  f = Cell(lambda x,y: x+y, name="f")(d,e)
  assert f() == 2*3+4*3
