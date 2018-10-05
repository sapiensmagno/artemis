(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryStatusDetailController', DeliveryStatusDetailController);

    DeliveryStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DeliveryStatus', 'Delivery'];

    function DeliveryStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, DeliveryStatus, Delivery) {
        var vm = this;

        vm.deliveryStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:deliveryStatusUpdate', function(event, result) {
            vm.deliveryStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
