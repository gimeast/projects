export default {
    build: {
        rollupOptions: {
            input: {
                main: 'index.html',
                login: '/login.html',
                admin_vehicles: '/pages/admin/vehicles.html',
                user_vehicles: '/pages/user/vehicles.html',
                user_maintenances: '/pages/user/maintenances.html',
            }
        }
    }
}
