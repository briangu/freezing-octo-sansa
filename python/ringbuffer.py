

class RingBuffer:

    def __init__(self, size):
        self.size = size
        self.buffer = [None]*size
        self.read_pos = -1
        self.write_pos = 0

    def read(self):
        if self.read_pos == -1:
            return None
        r = self.buffer[self.read_pos]
        self.read_pos = (self.read_pos + 1) % self.size
        if self.read_pos == self.write_pos:
            self.read_pos = -1
        return r

    def write(self, x):
        self.buffer[self.write_pos] = x
        if self.read_pos == -1:
            # first write on empty buffer
            self.read_pos = self.write_pos
        elif self.read_pos == self.write_pos:
            # write eats into read buffer
            self.read_pos = (self.read_pos + 1) % self.size
        # advance the write pointer
        self.write_pos = (self.write_pos + 1) % self.size


if __name__ == "__main__":
    ringBuffer = RingBuffer(2)
    assert ringBuffer.read() is None
    ringBuffer.write(2)
    assert ringBuffer.read() == 2
    assert ringBuffer.read() is None
    ringBuffer.write(3)
    assert ringBuffer.read() == 3
    assert ringBuffer.read() is None

    ringBuffer.write(4)
    ringBuffer.write(5)
    assert ringBuffer.read() == 4
    assert ringBuffer.read() == 5
    assert ringBuffer.read() is None

    ringBuffer.write(6)
    ringBuffer.write(7)
    ringBuffer.write(8)
    assert ringBuffer.read() == 7
    assert ringBuffer.read() == 8
    assert ringBuffer.read() is None

    ringBuffer.write(9)
    ringBuffer.write(10)
    ringBuffer.write(11)
    ringBuffer.write(12)
    assert ringBuffer.read() == 11
    assert ringBuffer.read() == 12
    ringBuffer.read() is None
    ringBuffer.read() is None
