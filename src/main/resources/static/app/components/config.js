angular.module('app')
    .config(function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/ads/new/:userId', {
            templateUrl: 'app/components/advertisements/add/adverAdd.html',
            controller: 'AdvertisementAddController',
            controllerAs: 'ctrl'
        })
        .when('/ads/edit/:adverId', {
            templateUrl: 'app/components/advertisements/add/adverEdit.html',
            controller: 'AdvertisementAddController',
            controllerAs: 'ctrl'
        })
        .when('/user-add', {
            templateUrl: 'app/components/users/userAdd/userAdd.html',
            controller: 'UserEditController',
            controllerAs: 'ctrl'
        })
        .when('/user-edit/:userId', {
            templateUrl: 'app/components/users/userAdd/userAdd.html',
            controller: 'UserEditController',
            controllerAs: 'ctrl'
        })
        .when('/admin-panel/allUsers', {
            templateUrl: 'app/components/adminPanel/users/userList.html',
            controller: 'UserListController',
            controllerAs: 'ctrl'
        })
        .when('/user-login',{
            templateUrl: 'app/components/users/userLogin/login.html',
            controller: 'AuthenticationController',
            controllerAs: 'ctrl'
        })
        .when('/user-logout', {
            templateUrl: 'app/components/users/userLogin/login.html',
            controller: 'AuthenticationController',
            controllerAs: 'ctrl'
        })
        .when('/home', {
            templateUrl: 'app/components/home/home.html',
            controller: 'HomeController',
            controllerAs: 'ctrl'
        })
        .when('/ads/:category', {
            templateUrl: 'app/components/advertisements/listByCategory/listByCategory.html',
            controller: 'ListController',
            controllerAs: 'ctrl'
        })
        .when('/ads/view/:adverId', {
            templateUrl: 'app/components/advertisements/view/adverView.html',
            controller: 'AdverViewController',
            controllerAs: 'ctrl'
        })
        .when('/accountView/:id', {
            templateUrl: 'app/components/users/account/accountView.html',
            controller: 'AccountController',
            controllerAs: 'ctrl'
        })
        .when('/archives/:id', {
            templateUrl: 'app/components/users/account/archiveAdvertisements/archives.html',
            controller: 'ArchivesController',
            controllerAs: 'ctrl'
        })
        .when('/resetPassword', {
            templateUrl: '/app/components/users/account/resetPassword/sendEmail/sendEmail.html',
            controller: 'SendEmailController',
            controllerAs: 'ctrl'
        })
        .when('/changePassword/:userId', {
            templateUrl: '/app/components/users/account/resetPassword/changePassword/changePassword.html',
            controller: 'ChangePasswordController',
            controllerAs: 'ctrl'
        })
        .when('/admin-panel',{
            templateUrl: '/app/components/adminPanel/adminPanel.html'
        })
        .when('/admin-panel/pendingAdvertisements', {
            templateUrl: '/app/components/adminPanel/advertisements/pending/adsToAccept.html',
            controller: 'AdsToAcceptController',
            controllerAs: 'ctrl'
        })
        .when('/admin-panel/pendingAdvertisements/toCorrection/:adverId', {
            templateUrl: '/app/components/adminPanel/advertisements/toCorrection/toCorrection.html',
            controller: 'ToCorrectionController',
            controllerAs: 'ctrl'
        })
        .otherwise({
            redirectTo: '/home',
        });
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
