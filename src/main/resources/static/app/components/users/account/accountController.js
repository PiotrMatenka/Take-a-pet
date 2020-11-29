angular.module('app')
.controller('AccountController', function ($routeParams, UserService, AdvertisementEndService, $location){
    var vm = this;
    vm.userId = $routeParams.id ;
    vm.user = UserService.get(vm.userId);
    vm.userAdvertisements = UserService.getUserAdvertisements(vm.userId);

    if (!vm.userId)
        $location.path('/home');

    const errorCallback = err => {
        vm.msg=`Błąd zapisu: ${err.data.message}`;
    };


    vm.finishAdvertisement = advertisement => {
        if (confirm("Czy na pewno chcesz zakończyć ogłoszenie?")) {
            AdvertisementEndService.save(advertisement.id)
                .then(response => {
                    advertisement.end = response.data;
                    location.reload();
                })
                .catch(errorCallback);
        }
        ;
    }
})