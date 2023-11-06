import numpy as np
import networkx as nx

def get_google_matrix(G, d=0.15):
    n = G.number_of_nodes()
    A = nx.to_numpy_array(G).T
    
    # for sink nodes
    is_sink = np.sum(A, axis=0)==0
    B = (np.ones_like(A) - np.identity(n)) / (n-1)
    A[:, is_sink] += B[:, is_sink]
    
    D_inv = np.diag(1/np.sum(A, axis=0))
    M = np.dot(A, D_inv) 
    
    # for disconnected components
    M = (1-d)*M + d*np.ones((n,n))/n
    return M


def pagerank_power(G, d=0.15, max_iter=100, eps=1e-9):
    M = get_google_matrix(G, d=d)
    n = G.number_of_nodes()
    V = np.ones(n)/n
    for _ in range(max_iter):
        V_last = V
        V = np.dot(M, V)
        if  l1(V-V_last)/n < eps:
            return V
    return V

def l1(x):
    return np.sum(np.abs(x))


G = nx.DiGraph()
G.add_edges_from([(1, 2), (2, 1), (2, 3), (3, 1), (3, 4), (4, 3)])

pagerank = pagerank_power(G, d=0.15)

for node, score in enumerate(pagerank):
    print(f'Node {node + 1}: {score:.4f}')