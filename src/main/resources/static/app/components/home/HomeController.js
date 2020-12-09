angular.module('app')
.controller("HomeController", function (CategoryService, AdvertisementService, $cookies, UserService, $http){
    const vm = this;

    if ($cookies.get("user"))
    {
        vm.userEmail = $cookies.get("user");
        vm.user = UserService.getByEmail(vm.userEmail);
        vm.userRole = UserService.getRoles(vm.userEmail);
    }

    vm.categories = CategoryService.getAll();

    vm.search = title => {
        vm.advertisements = AdvertisementService.getAll({title});
    }
})