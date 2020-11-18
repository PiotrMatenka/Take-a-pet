angular.module('app')
.controller('AdvertisementAddController', function ($routeParams, AdvertisementService, $location, CategoryService, Advertisement, ImagesService) {
    const vm = this;
    const adverId = $routeParams.adverId;
    const userId = $routeParams.userId;

    if (adverId){
        vm.advertisement = AdvertisementService.get(adverId);
        vm.images = ImagesService.getImagesById(adverId);}
    else
        vm.advertisement = new Advertisement();
        vm.advertisement.userId = userId;

    vm.categories = CategoryService.getAll()
    const saveCallback = () => {
        $location.path(`/ads/edit/${vm.advertisement.id}`);
        vm.msg = "Dodano ogłoszenie";
    };

    const errorCallback = err => {
        vm.msg=`Błąd zapisu: ${err.data.message}`;
    };

    vm.saveAdvertisement = () =>{
        AdvertisementService.save(vm.advertisement)
            .then(saveCallback)
            .catch(errorCallback);
    };

    vm.updateAdvertisement = () => {
        AdvertisementService.update(vm.advertisement)
            .then(saveCallback)
            .catch(errorCallback);
    }


});