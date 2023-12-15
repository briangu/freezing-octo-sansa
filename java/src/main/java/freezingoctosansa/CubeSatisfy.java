package freezingoctosansa;

import java.util.Random;

public class CubeSatisfy {



  static void satisfy() {

  }

  static void dump(byte[] arr) {
    for (byte b : arr) {
      System.out.print(String.format("%X", b));
    }
    System.out.println();
  }

  static void dumpTilesFromArray(byte[] arr) {
    int tileSize = (int)cubeRoot(arr.length);
    for (int tile = 0; tile < tileSize; tile++) {
      for (int row = 0; row < tileSize; row++) {
        int begin = (tile * (tileSize * tileSize)) + row * tileSize;
        int end = (tile * (tileSize * tileSize)) + (row + 1) * tileSize;
        for (int i = begin; i < end; i++) {
          System.out.print(String.format("%X", arr[i]));
        }
        System.out.println();
      }
      System.out.println();
    }
  }

  public static double cubeRoot(double x) {
    return Math.ceil(Math.pow(x, 1.0 / 3.0));
  }

  static void flatten(byte[] arr) {
     for (int i = 0; i < arr.length; i++) {
       if ((arr[i] & (byte)0x80) == (byte)0x80) {
          arr[i] = (byte)1;
       } else {
          arr[i] = (byte)0;
       }
     }
  }

  public static byte[][][] createTileCube(byte[] arr) {
    int tileSize = (int)cubeRoot(arr.length);

    byte[][][] tilesArr = new byte[tileSize][tileSize][tileSize];

    for (int tile = 0; tile < tileSize; tile++) {
      for (int row = 0; row < tileSize; row++) {
        int begin = (tile * (tileSize * tileSize)) + row * tileSize;
        int end = (tile * (tileSize * tileSize)) + (row + 1) * tileSize;
        for (int i = begin, j = 0; i < end; i++, j++) {
          tilesArr[tile][row][j] = arr[i];
        }
      }
    }

    return tilesArr;
  }

  public static void dumpTileCube(byte[][][] cube) {
    for (byte[][] tile : cube) {
      for (byte[] tileRow : tile) {
        for (byte col : tileRow) {
          System.out.print(String.format("%X", col));
        }
        System.out.println();
      }
      System.out.println();
    }
  }

  public static byte[][][] computeParityTilesFromTileCube(byte[][][] tileCube) {
    byte[][][] parityTiles = new byte[3][tileCube.length][tileCube.length];

    // tile 1
    for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
      for (int row = 0; row < tileCube.length; row++) {
        byte parity = 0;
        for (int col = 0; col < tileCube.length; col++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        parityTiles[0][row][tileIdx] = parity;
      }
    }

    // tile 2
    for (int row = 0; row < tileCube.length; row++) {
      for (int col = 0; col < tileCube.length; col++) {
        byte parity = 0;
        for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        parityTiles[1][row][col] = parity;
      }
    }

    // tile 3
    for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
      for (int col = 0; col < tileCube.length; col++) {
        byte parity = 0;
        for (int row = 0; row < tileCube.length; row++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        parityTiles[2][tileIdx][col] = parity;
      }
    }

    return parityTiles;
  }

  public static void dumpParityTiles(byte[][][] parityTiles) {
    for (byte[][] tile : parityTiles) {
      for (byte[] tileRow : tile) {
        for (byte col : tileRow) {
          System.out.print(String.format("%X", col));
        }
        System.out.println();
      }
      System.out.println();
    }
  }

  public static boolean isValidTileCube(byte[][][] tileCube, byte[][][] parityTiles) {
    // tile 1
    for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
      for (int row = 0; row < tileCube.length; row++) {
        byte parity = 0;
        for (int col = 0; col < tileCube.length; col++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        if (parityTiles[0][row][tileIdx] != parity) {
          return false;
        }
      }
    }

    // tile 2
    for (int row = 0; row < tileCube.length; row++) {
      for (int col = 0; col < tileCube.length; col++) {
        byte parity = 0;
        for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        if (parityTiles[1][row][col] != parity) {
          return false;
        }
      }
    }

    // tile 3
    for (int tileIdx = 0; tileIdx < tileCube.length; tileIdx++) {
      for (int col = 0; col < tileCube.length; col++) {
        byte parity = 0;
        for (int row = 0; row < tileCube.length; row++) {
          parity = (byte)((parity + tileCube[tileIdx][row][col]) & (byte)1);
        }
        if (parityTiles[2][tileIdx][col] != parity) {
          return false;
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {

    byte[] arr = new byte[64];

    Random r = new Random();
    r.nextBytes(arr);
    //dump(arr);
    flatten(arr);
    dump(arr);

    System.out.println("tiles from array:");
    dumpTilesFromArray(arr);

    byte[][][] tileCube = createTileCube(arr);

    System.out.println("tile cube:");
    dumpTileCube(tileCube);

    byte[][][] parityTiles = computeParityTilesFromTileCube(tileCube);

    System.out.println("parity tiles:");
    dumpParityTiles(parityTiles);

    boolean isValid = isValidTileCube(tileCube, parityTiles);
    System.out.println(String.format("isValid = %b", isValid));

    tileCube[0][0][0] = tileCube[0][0][0] == (byte)1 ? (byte)0 : (byte)1;
    isValid = isValidTileCube(tileCube, parityTiles);
    System.out.println(String.format("isValid = %b", isValid));
  }
}
