using BuisnessLogicLayer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services.NotificationObserver
{
    public interface ICommentObserver
    {
        void Update(PostModel postModel, CommentModel commentModel);
    }
}
