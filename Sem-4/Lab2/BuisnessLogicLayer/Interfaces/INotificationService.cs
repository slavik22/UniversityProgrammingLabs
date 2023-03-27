using DataAccessLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading.Tasks;

namespace BuisnessLogicLayer.Interfaces
{
    public interface INotificationService
    {
       Task SendNotificationToUser(int userId, string message);

       Task AddWebSocket(int userId, WebSocket webSocket); 
       Task RemoveWebSocket(int userId); 
    }
}
