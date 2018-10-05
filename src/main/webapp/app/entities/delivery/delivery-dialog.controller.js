(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryDialogController', DeliveryDialogController);

    DeliveryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Delivery', 'DeliveryStatus', 'Purchase'];

    function DeliveryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Delivery, DeliveryStatus, Purchase) {
        var vm = this;

        vm.delivery = entity;
        vm.clear = clear;
        vm.save = save;
        vm.deliverystatuses = DeliveryStatus.query();
        vm.purchases = Purchase.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.delivery.id !== null) {
                Delivery.update(vm.delivery, onSaveSuccess, onSaveError);
            } else {
                Delivery.save(vm.delivery, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artemisApp:deliveryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
