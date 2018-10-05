(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryStatusDialogController', DeliveryStatusDialogController);

    DeliveryStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeliveryStatus', 'Delivery'];

    function DeliveryStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeliveryStatus, Delivery) {
        var vm = this;

        vm.deliveryStatus = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deliveries = Delivery.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deliveryStatus.id !== null) {
                DeliveryStatus.update(vm.deliveryStatus, onSaveSuccess, onSaveError);
            } else {
                DeliveryStatus.save(vm.deliveryStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('artemisApp:deliveryStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastUpdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
