import sympy as sp
import numpy

x = sp.symbols('x')
f = x**4 + 4*x - 2
fl = sp.lambdify(x, f)

df = sp.diff(f, x)
dfl = sp.lambdify(x, df)

def dyhotomy(a, b, eps):
    i = 0
    while abs(fl(b)-fl(a)) > eps:
        i+=1
        mid = (a+b) / 2
        if b - a < eps: 
            print(f'Root x = {mid}')
            print(f'Iterations = {i}')
            break
        elif fl(a)*fl(mid) < 0:
            b = mid
        else:
            a = mid
    else:
        print('Root not found') 

def relaxation(start, finish, eps):
    xs = numpy.arange(start, finish, eps)

    min1 = min(abs(dfl(xs)))
    max1 = max(abs(dfl(xs)))

    print(f'm1: {min1}, M1: {max1}')

    xp = start
    xi = (start + finish) / 2

    tau = 2/(max1 + min1)

    print(f'tau: {tau}')

    v = tau * dfl(xi)
    if -2 < v and v < 0:
        print('Checked')
    else:
        return
    
    xis = []
    while (abs(xi - xp) > eps):
        xp = xi
        xis.append(xp)
        xi = xp + tau * fl(xp)

    print('Iterations: ')
    for i in range(len(xis)):
        print(f'  {i + 1}: {xis[i]}')


def newtonMethod(x0,iterationNumber, epsilon):
    x=x0
     
    for i in range(iterationNumber):
         if numpy.abs(fl(x)) < epsilon:
            print('Found solution after',i,'iterations.')
            return x
         if dfl(x) == 0:
            print('Zero derivative. No solution found.')
            return None

         x=x-fl(x)/dfl(x)
     
    print('Exceeded maximum iterations. No solution found.')
    return None

dyhotomy(-2,-1.5,1e-5)
print("----------------------------------")
relaxation(-2,-1.5,1e-5)
print("----------------------------------")
print(newtonMethod(-2,200, 1e-5))

