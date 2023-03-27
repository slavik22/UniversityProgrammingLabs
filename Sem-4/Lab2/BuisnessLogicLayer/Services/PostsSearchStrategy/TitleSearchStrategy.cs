using System;
using DataAccessLayer.Entities;

namespace BuisnessLogicLayer.Services.PostsSearchStrategy
{
    public class TitleSearchStrategy : ISearchStrategy
    {
        public IEnumerable<Post> Search(string query, IEnumerable<Post> posts)
        {
            return posts.Where(post => post.Title == query);
        }
    }
}

