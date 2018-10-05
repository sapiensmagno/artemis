(function() {
    'use strict';
    angular
        .module('artemisApp')
        .factory('DeliveryStatus', DeliveryStatus);

    DeliveryStatus.$inject = ['$resource', 'DateUtils'];

    function DeliveryStatus ($resource, DateUtils) {
        var resourceUrl =  'api/delivery-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastUpdate = DateUtils.convertDateTimeFromServer(data.lastUpdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
