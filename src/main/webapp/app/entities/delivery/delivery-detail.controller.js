(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryDetailController', DeliveryDetailController);

    DeliveryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Delivery', 'DeliveryStatus', 'Purchase'];

    function DeliveryDetailController($scope, $rootScope, $stateParams, previousState, entity, Delivery, DeliveryStatus, Purchase) {
        var vm = this;

        vm.delivery = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:deliveryUpdate', function(event, result) {
            vm.delivery = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
