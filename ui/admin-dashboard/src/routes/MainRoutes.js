import { lazy } from 'react';

// project imports
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';

// watch user page routing
const WatchUserPage = Loadable(lazy(() => import('views/twitter/watchTables')));

// account page routing
const AccountPage = Loadable(lazy(() => import('views/twitter/accountTables')));


// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: <MainLayout />,
    children: [
        {
            path: '/',
            element: <WatchUserPage />
        },
        {
            path: '/watch',
            element: <WatchUserPage />
        },
        {
            path: '/account',
            element: <AccountPage />
        }
    ]
};

export default MainRoutes;
