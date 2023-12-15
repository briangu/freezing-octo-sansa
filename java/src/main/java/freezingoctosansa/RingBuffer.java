package freezingoctosansa;

public class RingBuffer {

  private final Integer _size;
  private final Integer[] _buffer;
  private int _readPos;
  private int _writePos;

  public RingBuffer(int size) {
    _size = size;
    _buffer = new Integer[size];
    _readPos = -1;
    _writePos = 0;
  }

  public Integer read() {
    if (_readPos == -1) return null;
    Integer result = _buffer[_readPos];
    _readPos = (_readPos + 1) % _size;
    if (_readPos == _writePos) {
      _readPos = -1;
    }
    return result;
  }

  public void write(Integer item) {
    _buffer[_writePos] = item;
    if (_readPos == -1) {
      _readPos = _writePos;
    } else if (_readPos == _writePos) {
      _readPos = (_readPos + 1) % _size;
    }
    _writePos = (_writePos + 1) % _size;
  }
}
