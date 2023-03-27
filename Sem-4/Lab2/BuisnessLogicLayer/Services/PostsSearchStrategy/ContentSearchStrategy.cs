using System;
using DataAccessLayer.Entities;

namespace BuisnessLogicLayer.Services.PostsSearchStrategy
{
    public class ContentearchStrategy : ISearchStrategy
    {
        public IEnumerable<Post> Search(string query, IEnumerable<Post> posts)
        {
            return posts.Where(post => post.Content == query);
        }
    }
}

