using BuisnessLogicLayer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services.NotificationObserver
{
    public class CommentSubject : ICommentSubject
    {
        private readonly List<ICommentObserver> _observers = new List<ICommentObserver>();

        public void Attach(ICommentObserver observer)
        {
            _observers.Add(observer);
        }

        public void Detach(ICommentObserver observer)
        {
            _observers.Remove(observer);
        }

        public void Notify(PostModel postModel, CommentModel commentModel)
        {
            _observers.ForEach(o => o.Update(postModel, commentModel));
        }
    }
}
