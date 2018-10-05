(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryDeleteController',DeliveryDeleteController);

    DeliveryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Delivery'];

    function DeliveryDeleteController($uibModalInstance, entity, Delivery) {
        var vm = this;

        vm.delivery = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Delivery.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
