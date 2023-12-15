

def draw_star(n):
    if n <= 0:
        return
    k = 2*n-1
    for i in range(1,k+1):
        x = i if i <= n else n-(i-n)
        print(" "*(n-x) + "*"*(2*x-1))

"""
*

 *  i=1, x=i,  2-x = 1, 2*x-1 = 1
*** i=2, x=i,  2-x = 0, 2*x-1 = 3
 *  i=3, x=i-n,2-x = 1, 2*x-1 = 1

  *     i=1,x=i=1,n-x=2,2*x-1=1
 ***    i=2,x=i=2,n-x=1,2*x-1=3
*****   i=3,x=i=3,n-x=0,2*x-1=5
 ***    i=4,x=n-(i-n)=3-1=2,
  *     i=5,x=n-(i-n)=3-2=1,

"""

if __name__ == "__main__":
    for i in range(1,10):
        draw_star(i)
