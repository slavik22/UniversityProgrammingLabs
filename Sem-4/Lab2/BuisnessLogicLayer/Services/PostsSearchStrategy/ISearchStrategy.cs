using System;
using DataAccessLayer.Entities;

namespace BuisnessLogicLayer.Services.PostsSearchStrategy
{
	public interface ISearchStrategy
	{
        IEnumerable<Post> Search(string query, IEnumerable<Post> posts);
    }
}

