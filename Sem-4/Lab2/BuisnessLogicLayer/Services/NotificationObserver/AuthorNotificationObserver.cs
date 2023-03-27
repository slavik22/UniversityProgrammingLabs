using BuisnessLogicLayer.Interfaces;
using BuisnessLogicLayer.Models;
using System.Net.WebSockets;

namespace BuisnessLogicLayer.Services.NotificationObserver
{
    public class AuthorNotificationObserver : ICommentObserver
    {
        private readonly INotificationService _notificationService;
        private readonly WebSocket _webSocket;

        public AuthorNotificationObserver(INotificationService notificationService, WebSocket webSocket)
        {
            _notificationService = notificationService;
            _webSocket = webSocket;
        }

        public async void Update(PostModel postModel, CommentModel commentModel)
        {
            if(postModel.AuthorName != commentModel.AuthorName)
            {
                return;
            }

           await SendNotificationToUser(postModel.UserId, $"New comment added to your post {postModel.Title}");

        }
        public async Task SendNotificationToUser(int userId, string message)
        {
            await _notificationService.SendNotificationToUser(userId, message);

            if (_notificationService is INotificationService notificationService)
            {
                if (_webSocket != null && _webSocket.State == WebSocketState.Open)
                {
                    await notificationService.AddWebSocket(userId, _webSocket);
                }
            }
        }
    }
}
