(function() {
    'use strict';
    angular
        .module('artemisApp')
        .factory('Delivery', Delivery);

    Delivery.$inject = ['$resource'];

    function Delivery ($resource) {
        var resourceUrl =  'api/deliveries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
