angular.module('app')
    .config(function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/ads/new', {
            templateUrl: 'app/components/advertisements/add/adverAdd.html',
            controller: 'AdvertisementAddController',
            controllerAs: 'ctrl'
        })
        .when('/ads/edit/:adverId', {
            templateUrl: 'app/components/advertisements/add/adverAdd.html',
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
        .when('/allUsers', {
            templateUrl: 'app/components/users/userList/userList.html',
            controller: 'UserListController',
            controllerAs: 'ctrl'
        })
        .when('/user-login',{
            templateUrl: 'app/components/users/userLogin/loginJwt.html',
            controller: 'LoginController',
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
        .otherwise({
            redirectTo: '/home',
        });
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
