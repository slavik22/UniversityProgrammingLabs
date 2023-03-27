using System;
using DataAccessLayer.Entities;

namespace BuisnessLogicLayer.Services.PostsSearchStrategy
{
    public class AuthorSearchStrategy : ISearchStrategy
    {
        public IEnumerable<Post> Search(string query, IEnumerable<Post> posts)
        {
            return posts.Where(post => post.User.Name == query || post.User.Surname == query);
        }
    }
}

