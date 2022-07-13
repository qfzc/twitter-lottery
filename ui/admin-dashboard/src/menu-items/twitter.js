// assets
import { IconBrandTwitter, IconUsers } from '@tabler/icons';

// constant
const icons = {
    IconBrandTwitter,
    IconUsers
};

// ==============================|| UTILITIES MENU ITEMS ||============================== //

const twitter = {
    id: 'twitter',
    title: 'Twitter',
    type: 'group',
    caption: 'Twitter 撸白',
    children: [
        {
            id: 'watch',
            title: '监控列表',
            type: 'item',
            url: '/watch',
            icon: icons.IconBrandTwitter,
            breadcrumbs: false
        },
        {
            id: 'account',
            title: '账号列表',
            type: 'item',
            url: '/account',
            icon: icons.IconUsers,
            breadcrumbs: false
        }
    ]
};

export default twitter;
