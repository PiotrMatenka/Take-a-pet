angular.module('app')
    .constant('ADVERTISEMENT_ENDPOINT', '/api/advertisements/:id')
    .constant('ADS_BY_CATEGORY', '/api/advertisements/findByCategory/:category')
    .constant('AD_VIEW_BY_ID', '/api/advertisements/view/:id')
    .constant('ADS_ACCEPTED_BY_USER', '/api/checkingAdvertisements/acceptedByUser')

.factory('Advertisement', function ($resource, ADVERTISEMENT_ENDPOINT, ADS_BY_CATEGORY, AD_VIEW_BY_ID, ADS_ACCEPTED_BY_USER) {
    return $resource(ADVERTISEMENT_ENDPOINT,{id: '@_id'},{
        update:{
            method: 'PUT'
        },
        getAllByCategory:{
            method: 'GET',
            url: ADS_BY_CATEGORY,
            params: {category: '@category'},
            isArray: true
        },
        getViewById: {
            method:'GET',
            url: AD_VIEW_BY_ID
        },
        getAcceptedByUser: {
            method: 'GET',
            url: ADS_ACCEPTED_BY_USER,
            isArray: true
        }
    });
})
    .service('AdvertisementService', function (Advertisement) {
        this.getAll = params => Advertisement.query(params);
        this.save = advertisement => advertisement.$save();
        this.update = advertisement => advertisement.$update({id: advertisement.id});
        this.get = index => Advertisement.get({id: index});
        this.getAllByCategory = index => Advertisement.getAllByCategory({category: index});
        this.getViewById = index => Advertisement.getViewById({id: index});
        this.getAcceptedByUser = () => Advertisement.getAcceptedByUser();
})
    .service('AdvertisementCheckService', function ($http) {
        this.save = advertisementId => $http.post(`/api/checkingAdvertisements/${advertisementId}/end`);
        this.acceptByUser = advertisementId => $http.post(`/api/checkingAdvertisements/${advertisementId}/userAccept`);
        this.acceptByAdmin = advertisementId => $http.post(`/api/checkingAdvertisements/${advertisementId}/adminAccept`);
    });