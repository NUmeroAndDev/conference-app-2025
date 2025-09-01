import Component
import Model
import SwiftUI
import Theme

struct ProfileCardEditScreen: View {
    @Binding var presenter: ProfileCardPresenter
    @Environment(\.dismiss) private var dismiss

    var body: some View {
        ScrollView {
            EditProfileCardForm(presenter: $presenter)
            .padding(.bottom, 80)
        }
        .background(AssetColors.surface.swiftUIColor)
        .navigationTitle("Edit Profile Card")
        .navigationBarTitleDisplayMode(.large)
        .navigationBarBackButtonHidden(presenter.profile.profile == nil)
        .onChange(of: presenter.isEditing) { _, isEditing in
            // Close the screen when editing is complete
            if !isEditing && presenter.profile.profile != nil {
                dismiss()
            }
        }
    }
}
