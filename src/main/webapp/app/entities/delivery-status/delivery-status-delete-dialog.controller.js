(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryStatusDeleteController',DeliveryStatusDeleteController);

    DeliveryStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeliveryStatus'];

    function DeliveryStatusDeleteController($uibModalInstance, entity, DeliveryStatus) {
        var vm = this;

        vm.deliveryStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeliveryStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
