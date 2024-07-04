document.addEventListener('DOMContentLoaded', function() {
    const notificationsBtn = document.getElementById('notifications-btn');
    const notificationPopup = document.getElementById('notification-popup');
    const notificationList = document.getElementById('notification-list');
    const notificationCount = document.getElementById('notification-count');

    let notificationsData = []; // Almacena las notificaciones en memoria

    // Función para obtener las notificaciones del servidor
    function fetchNotifications() {
        return fetch('/notifications')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al obtener las notificaciones');
                }
                return response.json();
            })
            .then(data => {
                notificationsData = data; // Almacena las notificaciones en memoria
                return data;
            })
            .catch(error => {
                console.error('Error al obtener las notificaciones:', error);
            });
    }

    // Función para contar y mostrar las notificaciones no leídas
    function countAndRenderNotifications(notifications) {
        const unreadCount = notifications.filter(notification => !notification.leido).length;
        notificationCount.innerText = unreadCount;
        renderNotificationList(notifications);
    }

    // Función para renderizar la lista completa de notificaciones
 // Función para renderizar la lista completa de notificaciones
 function renderNotificationList(notifications) {
     notificationList.innerHTML = '';

     notifications.forEach(notification => {
         const listItem = document.createElement('li');
         const isRead = notification.leido;
         const fontWeight = isRead ? '300' : '600'; // Peso de fuente diferente para leídas y no leídas

         listItem.classList.add('notification-item', isRead ? 'read' : 'unread');

         listItem.innerHTML = `
             <div class="notification-content">
                 <div class="notification-profile">
                     <img src="${notification.profileImage}" alt="Perfil">
                 </div>
                 <div class="notification-details">
                     <div class="notification-message" style="font-weight: ${fontWeight};">${notification.message}</div>
                     <div class="notification-date">${notification.age}</div>
                 </div>
             </div>
         `;

         listItem.addEventListener('click', function() {
             markNotificationAsRead(notification.id, listItem, notification.post.id);
             window.location.href = `/postopen/${notification.post.id}`; // Redirigir a la publicación por su ID
         });

         notificationList.appendChild(listItem);
     });
 }


    // Función para marcar una notificación como leída
    function markNotificationAsRead(id, listItem, postId) {
        fetch(`/notifications/markAsRead/${id}`, { method: 'POST' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al marcar la notificación como leída');
                }
                listItem.classList.remove('unread');
                listItem.classList.add('read');

                // Actualizar el estado en la memoria
                notificationsData = notificationsData.map(notification => {
                    if (notification.id === id) {
                        return { ...notification, leido: true };
                    }
                    return notification;
                });

                // Actualizar el contador de notificaciones no leídas
                const unreadCount = notificationsData.filter(notification => !notification.leido).length;
                notificationCount.innerText = unreadCount;
            })
            .catch(error => {
                console.error('Error al marcar la notificación como leída:', error);
            });
    }

    // Evento al hacer clic en el botón de notificaciones
    notificationsBtn.addEventListener('click', function(event) {
        event.preventDefault();

        if (notificationPopup.style.display === 'block') {
            closeNotificationPopup(); // Cierra el popup si ya está abierto
        } else {
            openNotificationPopup(); // Abre el popup si está cerrado
        }
    });

    // Función para abrir el popup de notificaciones
    function openNotificationPopup() {
        fetchNotifications()
            .then(notifications => {
                countAndRenderNotifications(notifications);
                notificationPopup.style.display = 'block';
                notificationsBtn.classList.add('active'); // Agrega la clase activa
            })
            .catch(error => {
                console.error('Error al abrir el popup de notificaciones:', error);
            });
    }

    // Función para cerrar el popup de notificaciones
    function closeNotificationPopup() {
        notificationPopup.style.display = 'none';
        notificationsBtn.classList.remove('active'); // Quita la clase activa
    }

    // Cerrar el popup si se hace clic fuera de él
    document.addEventListener('click', function(event) {
        if (!notificationPopup.contains(event.target) && event.target !== notificationsBtn) {
            closeNotificationPopup();
        }
    });

    // Cargar las notificaciones al cargar la página por primera vez
    fetchNotifications()
        .then(notifications => countAndRenderNotifications(notifications))
        .catch(error => {
            console.error('Error al cargar las notificaciones:', error);
        });
});

    function searchPosts() {
      var query = document.getElementById('search-input').value.trim();
      if (query.length === 0) {
          alert('Por favor ingresa un término de búsqueda.');
          return;
      }
      // Redirigir a la ruta manejada por Spring Boot
      window.location.href = '/search?q=' + encodeURIComponent(query);
  }

    document.addEventListener('DOMContentLoaded', function() {
        var menuItems = document.querySelectorAll('.sidebar-content nav ul li');
        document.querySelector('.sidebar-content nav ul li:first-child').classList.add('active');
    });
