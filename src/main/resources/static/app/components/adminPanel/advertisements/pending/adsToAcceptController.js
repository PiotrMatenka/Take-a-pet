angular.module('app')
.controller("AdsToAcceptController", function (AdvertisementCheckService, AdvertisementService){
    var vm = this;
    vm.advertisements = AdvertisementService.getAcceptedByUser();

    const errorCallback = err => {
        if (err.status == 409){
            vm.msg=`Błąd zapisu: ${err.data.message}`;}
        else {
            vm.msg = 'Podano błędne dane';
        }
    };

    const successCallback = () => {
        location.reload();
    };

    vm.acceptAdvertisement = advertisement =>{
        if (confirm("Czy na pewno chcesz zaakceptować ogłoszenie?")){
            AdvertisementCheckService.acceptByAdmin(advertisement.id)
                .then(successCallback)
                .catch(errorCallback);
        }};

});