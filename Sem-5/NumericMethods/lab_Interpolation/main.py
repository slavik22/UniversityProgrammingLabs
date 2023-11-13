import numpy as np
import matplotlib.pyplot as plt
from sympy import symbols, lambdify

def f(x):
    return 2 * x**2 - x + 1

x_symbolic = symbols('x')

f_symbolic = f(x_symbolic)

f_numeric = lambdify(x_symbolic, f_symbolic, 'numpy')

def lagrange_interpolation(x_vals, y_vals, x):
    result = 0.0
    n = len(x_vals)

    for i in range(n):
        term = y_vals[i]
        for j in range(n):
            if j != i:
                term = term * (x - x_vals[j]) / (x_vals[i] - x_vals[j])
        result += term

    return result

def divided_difference(x_vals, y_vals):
    n = len(x_vals)
    if n != len(y_vals):
        raise ValueError("Input arrays must have the same length.")

    table = np.zeros((n, n))
    table[:, 0] = y_vals

    for j in range(1, n):
        for i in range(n - j):
            table[i, j] = (table[i + 1, j - 1] - table[i, j - 1]) / (x_vals[i + j] - x_vals[i])

    return table[0, :]

def newton_interpolation(x_vals, y_vals, x):
    n = len(x_vals)
    coeffs = divided_difference(x_vals, y_vals)

    result = coeffs[0]
    product_term = 1.0

    for i in range(1, n):
        product_term *= (x - x_vals[i - 1])
        result += coeffs[i] * product_term

    return result

def cubic_spline_coefficients(x_data, y_data):
    n = len(x_data)
    h = [x_data[i + 1] - x_data[i] for i in range(n - 1)]
    
    A = np.zeros((n, n))
    for i in range(1, n - 1):
        A[i, i - 1:i + 2] = [h[i - 1], 2 * (h[i - 1] + h[i]), h[i]]
    
    A[0, 0] = 1
    A[-1, -1] = 1
    
    b = [0] + [3 * ((y_data[i + 1] - y_data[i]) / h[i] - (y_data[i] - y_data[i - 1]) / h[i - 1]) for i in range(1, n - 1)] + [0]
    
    M = np.linalg.solve(A, b)
    
    a = y_data[:-1]
    b = [(y_data[i + 1] - y_data[i]) / h[i] - h[i] * (M[i + 1] + 2 * M[i]) / 3 for i in range(n - 1)]
    c = [M[i] for i in range(n - 1)]
    d = [(M[i + 1] - M[i]) / (3 * h[i]) for i in range(n - 1)]
    
    return a, b, c, d

def evaluate_cubic_spline(x, x_data, a, b, c, d):
    i = np.searchsorted(x_data, x) - 1
    h = x_data[i + 1] - x_data[i]
    return a[i] + b[i] * (x - x_data[i]) + c[i] * (x - x_data[i])**2 + d[i] * (x - x_data[i])**3


def plot_interpolations(f, a, b, n):
    x_vals = np.linspace(a, b, n)
    y_vals = f_numeric(x_vals)
    x_interp = np.linspace(a, b, 1000)

    c_a, c_b, c_c, c_d = cubic_spline_coefficients(x_vals, y_vals)

    lagrange_interp = [lagrange_interpolation(x_vals, y_vals, x) for x in x_interp]
    newton_interp = [newton_interpolation(x_vals, y_vals, x) for x in x_interp]
    cubic_spline_interp = [evaluate_cubic_spline(x, x_vals, c_a, c_b, c_c, c_d ) for x in x_interp]

    
    plt.figure(figsize=(10, 6))
    plt.plot(x_interp, f_numeric(x_interp), label='Original Function', linestyle='dashed', color='black')
    plt.scatter(x_vals, y_vals, label='Interpolation Points', color='red')
    # plt.plot(x_interp, lagrange_interp, label='Lagrange Interpolation', linestyle='dashed', color='blue')
    # plt.plot(x_interp, newton_interp, label='Newton Interpolation', linestyle='dashed', color='green')
    plt.plot(x_interp, cubic_spline_interp, label='Cubic Spline Interpolation', linestyle='dashed', color='orange')

    plt.title('Interpolation Comparison')
    plt.xlabel('x')
    plt.ylabel('f(x)')
    plt.legend()
    plt.grid(True)
    plt.show()

a = -2
b = 2
n = 10
plot_interpolations(f_numeric, a, b, n)

