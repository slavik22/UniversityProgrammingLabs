import numpy as np

def pagerank(graph, damping_factor=0.85, max_iterations=100, tolerance=1e-6):
    num_pages = len(graph)
    
    # Initialize the transition matrix
    transition_matrix = np.zeros((num_pages, num_pages))
    for i in range(num_pages):
        outgoing_links = sum(graph[i])
        for j in range(num_pages):
            if outgoing_links == 0:
                transition_matrix[i, j] = 1 / num_pages
            else:
                transition_matrix[i, j] = damping_factor * (graph[i][j] / outgoing_links) + (1 - damping_factor) / num_pages
    
    # Initialize the PageRank vector
    pagerank_vector = np.ones(num_pages) / num_pages
    
    for iteration in range(max_iterations):
        new_pagerank_vector = np.dot(transition_matrix, pagerank_vector)
        
        # Check for convergence
        if np.linalg.norm(new_pagerank_vector - pagerank_vector) < tolerance:
            return new_pagerank_vector
        
        pagerank_vector = new_pagerank_vector

    return pagerank_vector

# Example usage:
# Define a simple web graph as an adjacency matrix
graph = [
    [0, 1, 0, 0],
    [1, 0, 1, 0],
    [0, 1, 0, 1],
    [0, 0, 1, 0]
]

pagerank_scores = pagerank(graph)
for i, score in enumerate(pagerank_scores):
    print(f'Page {i + 1}: {score:.4f}')
