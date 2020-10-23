angular.module('app')
.controller("HomeController", function (CategoryService, AdvertisementService, AuthService, $scope){
    const vm = this;
    $scope.user = AuthService.user;
    vm.categories = CategoryService.getAll();

    vm.search = title => {
        vm.advertisements = AdvertisementService.getAll({title});
    }
})