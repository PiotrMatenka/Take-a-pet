angular.module('app')
    .constant('USER_ENDPOINT', '/api/users/:id')
    .constant('USER_BY_EMAIL_ENDPOINT', '/api/users/byEmail/:email')
    .constant('USER_ADVERTISEMENTS', '/api/users/:id/advertisements')
.factory('User', function ($resource, USER_ENDPOINT, USER_BY_EMAIL_ENDPOINT, USER_ADVERTISEMENTS) {
    return $resource(USER_ENDPOINT, { id: '@_id' },{
        update: {
            method: 'PUT'
        },
        getByEmail: {
            method: 'GET',
            url: USER_BY_EMAIL_ENDPOINT,
            params: {email: '@email'},
            isArray: false
        },
        getUserAdvertisements: {
            method:'GET',
            url: USER_ADVERTISEMENTS,
            params: {id: '@id'},
            isArray: true
        }
    });
})
.service('UserService', function(User) {
    this.getAll = params => User.query(params);
    this.get = index => User.get({id: index});
    this.save = user => user.$save();
    this.update = user => user.$update({id: user.id});
    this.getRoles = index => User.getRoles({id: index});
    this.getByEmail = index => User.getByEmail({email: index});
    this.getUserAdvertisements = index => User.getUserAdvertisements({id: index});
});