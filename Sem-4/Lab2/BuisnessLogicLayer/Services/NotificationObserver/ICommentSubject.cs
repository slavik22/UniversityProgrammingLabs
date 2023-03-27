using BuisnessLogicLayer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services.NotificationObserver
{
    public interface ICommentSubject
    {
        void Attach(ICommentObserver observer);
        void Detach(ICommentObserver observer);
        void Notify(PostModel postModel, CommentModel commentModel);
    }
}
