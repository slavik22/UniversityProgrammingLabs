using System;
using DataAccessLayer.Entities;

namespace BuisnessLogicLayer.Services.PostsSearchStrategy
{
	public class BlogPostSearchContext
	{
        private readonly ISearchStrategy _searchStrategy;

        public BlogPostSearchContext(ISearchStrategy searchStrategy)
        {
            _searchStrategy = searchStrategy;
        }

        public IEnumerable<Post> Search(string query, IEnumerable<Post> posts)
        {
            return _searchStrategy.Search(query, posts);
        }
    }
}

