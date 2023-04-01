using BuisnessLogicLayer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Services
{
    public class NotificationService : INotificationService
    {
        public Task AddWebSocket(int userId, WebSocket webSocket)
        {
            throw new NotImplementedException();
        }

        public Task RemoveWebSocket(int userId)
        {
            throw new NotImplementedException();
        }

        public Task SendNotificationToUser(int userId, string message)
        {
            throw new NotImplementedException();
        }
    }
}
