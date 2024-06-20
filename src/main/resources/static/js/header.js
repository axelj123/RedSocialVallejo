   document.addEventListener('DOMContentLoaded', function() {
        const notificationsBtn = document.getElementById('notifications-btn');
        const notificationPopup = document.getElementById('notification-popup');
        const notificationList = document.getElementById('notification-list');
        const notificationCount = document.getElementById('notification-count');


        // Función para obtener y mostrar las notificaciones al cargar la página
        function fetchNotificationsAndCount() {
            fetch('/notifications')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al obtener las notificaciones');
                    }
                    return response.json();
                })
                .then(data => {
                    notificationCount.innerText = data.length; // Actualiza el conteo de notificaciones
                })
                .catch(error => {
                    console.error('Error al obtener las notificaciones:', error);
                });
        }

        // Evento al hacer clic en el botón de notificaciones
        notificationsBtn.addEventListener('click', function(event) {
            event.preventDefault();

            if (notificationPopup.style.display === 'block') {
                notificationPopup.style.display = 'none';
                            notificationsBtn.classList.remove('active'); // Quita la clase activa

            } else {
                fetchNotificationsAndShowList(); // Muestra la lista completa al hacer clic
            }
        });

        // Función para mostrar la lista completa de notificaciones
        function fetchNotificationsAndShowList() {
            fetch('/notifications')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al obtener las notificaciones');
                    }
                    return response.json();
                })
                .then(data => {
                    notificationList.innerHTML = '';

                    data.forEach(notification => {
                        const listItem = document.createElement('li');
                        listItem.classList.add('notification-item');
                        listItem.innerHTML = `
                            <div class="notification-content">
                                <div class="notification-profile">
                                    <img src="${notification.profileImage}" alt="Perfil">
                                </div>
                                <div class="notification-details">
                                    <div class="notification-message">${notification.message}</div>

                                  <div class="notification-date">${notification.age}</div>

                                </div>
                            </div>
                        `;

                        listItem.addEventListener('click', function() {
                            markNotificationAsRead(notification.id);
                        window.location.href = `/postopen/${notification.post.id}`; // Redirigir a la publicación por su ID

                        });

                        notificationList.appendChild(listItem);
                    });

                    notificationPopup.style.display = 'block';
                                    notificationsBtn.classList.add('active'); // Agrega la clase activa

                })
                .catch(error => {
                    console.error('Error al obtener las notificaciones:', error);
                });
        }

        // Función para marcar una notificación como leída
        function markNotificationAsRead(id) {
            fetch(`/notifications/markAsRead/${id}`, { method: 'POST' })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al marcar la notificación como leída');
                    }
                    return response.json();
                })
                .then(() => {
                    fetchNotificationsAndCount(); // Actualiza el conteo después de marcar como leída
                })
                .catch(error => {
                    console.error('Error al marcar la notificación como leída:', error);
                });
        }

        // Función para cerrar el popup si se hace clic fuera de él
        document.addEventListener('click', function(event) {
            if (!notificationPopup.contains(event.target) && event.target !== notificationsBtn) {
                notificationPopup.style.display = 'none';
                            notificationsBtn.classList.remove('active'); // Quita la clase activa

            }
        });

        // Cargar las notificaciones al cargar la página por primera vez
        fetchNotificationsAndCount();
    });