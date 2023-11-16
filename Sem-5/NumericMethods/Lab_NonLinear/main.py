import numpy as np
from numpy import cos, sin, pi, exp, sqrt,abs

def F(x):
        return np.array(
            [x[0]**2 - 2*x[0]*x[1] + 1,
            x[0]**2 + x[1]**2 - 2])

def J(x):
        return np.array([
            [2*x[0] - 2*x[1],-2*x[0]],
            [2*x[0],2*x[1]]
            ])


def Newton_system(F, J, x, eps):
    F_value = F(x)
    F_norm = np.linalg.norm(F_value, ord=2)
    iteration_counter = 0
    while abs(F_norm) > eps and iteration_counter < 100:
        delta = np.linalg.solve(J(x), -F_value)
        x = x + delta
        F_value = F(x)
        F_norm = np.linalg.norm(F_value, ord=2)
        iteration_counter += 1

    if abs(F_norm) > eps:
        iteration_counter = -1
    return x, iteration_counter


def relaxation_method(F, x0, tau=0.3, tol=1e-6, max_iter=100):
    x = x0.copy()
    for i in range(max_iter):
        x1 = x - tau * F(x)
        if np.linalg.norm(x1 - x) < tol:
            return x1
        x = x1
    return None


def test_newton():
    expected = np.array([1, 1])
    tol = 1e-4
    x, n = Newton_system(F, J, x=np.array([2, -1]), eps=0.0001)
    print ("Newton solution: ", n, x)
    error_norm = np.linalg.norm(expected - x, ord=2)
    assert error_norm < tol, 'norm of error =%g' % error_norm
    print ('Newton norm of error =%g' % error_norm)

def test_relaxation():
    expected = np.array([1, 1])
    solution = relaxation_method(F, np.array([0.7, 0.7]))

    if solution is not None:
        print("Relaxation solution:", solution)
    else:
        print("Convergence not reached within the maximum number of iterations.")

test_relaxation()
test_newton()